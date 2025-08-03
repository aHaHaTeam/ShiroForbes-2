import {useAuth} from "@/utils/AuthContext.jsx";
import {useNavigate} from "react-router-dom";

export function useApiFetch() {
    const auth = useAuth();
    const accessToken = auth.accessToken;
    const refreshToken = localStorage.getItem("refreshToken");

    return async function (url, options = {}, retry = true) {
        const headers = {
            ...(options.headers || {}),
            "Content-Type": "application/json",
            ...(accessToken && {Authorization: `${accessToken}`})
        };
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

                return await fetch(url, {...options, headers});
            } else {
                localStorage.removeItem("accessToken");
                localStorage.removeItem("refreshToken");
                const navigate = useNavigate();
                navigate("/login")
            }
        }

        return res;
    }
}
