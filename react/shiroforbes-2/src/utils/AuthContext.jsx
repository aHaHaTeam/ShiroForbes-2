import { createContext, useContext, useState } from "react";

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
    const [accessToken, setAccessToken] = useState(() => localStorage.getItem("accessToken"));
    const [refreshToken, setRefreshToken] = useState(() => localStorage.getItem("refreshToken"));
    const [role, setRole] = useState(() => localStorage.getItem("role"));
    const [login, setLogin] = useState(()=>localStorage.getItem("login"))
    const [password, setPassword] = useState(() => localStorage.getItem("password"));

    const Login = ({ accessToken, refreshToken, role, login, password }) => {
        localStorage.setItem("accessToken", accessToken);
        localStorage.setItem("refreshToken", refreshToken);
        localStorage.setItem("role", role);
        localStorage.setItem("login", login);
        localStorage.setItem("password", password);
        setAccessToken(accessToken);
        setRefreshToken(refreshToken);
        setRole(role);
        setLogin(login);
        setPassword(password);
    };

    const Logout = () => {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        localStorage.removeItem("role");
        setAccessToken(null);
        setRefreshToken(null);
        setRole(null);
    };

    const isAuthenticated = !!accessToken;

    return (
        <AuthContext.Provider value={{ accessToken, refreshToken, Login, Logout, role, isAuthenticated }}>
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    return useContext(AuthContext);
}
