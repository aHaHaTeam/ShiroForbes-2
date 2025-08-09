import {createContext, useContext, useState} from "react";
import {useData} from "@/utils/DataContext.jsx";

const AuthContext = createContext(null);

export function AuthProvider({children}) {
    const [accessToken, setAccessToken] = useState(() => localStorage.getItem("accessToken"));
    const [refreshToken, setRefreshToken] = useState(() => localStorage.getItem("refreshToken"));
    const [role, setRole] = useState(() => localStorage.getItem("role"));
    const [login, setLogin] = useState(() => localStorage.getItem("login"))


    const Login = ({accessToken, refreshToken, role, login}) => {
        localStorage.setItem("accessToken", accessToken);
        localStorage.setItem("refreshToken", refreshToken);
        localStorage.setItem("role", role);
        localStorage.setItem("login", login);
        setAccessToken(accessToken);
        setRefreshToken(refreshToken);
        setRole(role);
        setLogin(login);
    };

    const Logout = () => {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        localStorage.removeItem("role");
        localStorage.removeItem("login");
        localStorage.removeItem("password");
        setAccessToken(null);
        setRefreshToken(null);
        setRole(null);
        setLogin(null);

    };

    const isAuthenticated = !!accessToken;

    return (
        <AuthContext.Provider value={{accessToken, refreshToken, Login, Logout, role, isAuthenticated}}>
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    return useContext(AuthContext);
}
