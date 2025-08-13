import {useAuth} from "@/utils/AuthContext.jsx";

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
        console.log(headers);
        console.log(accessToken && {"Authorization": `${accessToken}`});
        console.log(accessToken);
        const res = await fetch(url, {...options, headers});

        if (res.status === 401 && retry && refreshToken) {
            const refreshRes = await fetch("/api/auth/refresh", {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({refreshToken})
            });

            if (refreshRes.ok) {
                const data = await refreshRes.json();
                localStorage.setItem("accessToken", data.accessToken);
                localStorage.setItem("refreshToken", data.refreshToken);

                const newAccessToken = data.accessToken;
                const newHeaders = {
                    ...(options.headers || {}),
                    "Content-Type": "application/json",
                    ...(newAccessToken && {Authorization: `${newAccessToken}`})
                };
                return await fetch(url, {...options, headers: newHeaders});
            } else {
                localStorage.removeItem("accessToken");
                localStorage.removeItem("refreshToken");
            }
        }

        return res;
    }
}
