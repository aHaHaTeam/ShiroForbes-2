import {useAuth} from "@/utils/AuthContext.jsx";
import {toast} from "sonner";

export function useApiFetch() {
    const auth = useAuth();
    const accessToken = auth.accessToken;
    const refreshToken = localStorage.getItem("refreshToken");

    return async function (url, options = {}, retry = true) {
        const headers = {
            ...(options.headers || {}),
            "Content-Type": "application/json",
            "Authorization": `${accessToken}`
        };
        const res = await fetch(url, {...options, headers});

        if (res.status === 401 && retry && refreshToken) {
            toast("Ошибка аутентификации");
            const refreshRes = await fetch("/api/auth/refresh", {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({refreshToken})
            });

            if (refreshRes.ok) {
                const data = await refreshRes.json();
                localStorage.setItem("accessToken", data.accessToken);

                const newAccessToken = data.accessToken;
                const newHeaders = {
                    ...(options.headers || {}),
                    "Content-Type": "application/json",
                    ...(newAccessToken && {Authorization: `${newAccessToken}`})
                };
                return await fetch(url, {...options, headers: newHeaders});
            } else {
                console.log("ошибка была но ничего не случилось");
                localStorage.removeItem("accessToken");
                localStorage.removeItem("refreshToken");
                window.location.replace("/login");
                window.location.href = "/login";
            }
        }

        return res;
    }
}
