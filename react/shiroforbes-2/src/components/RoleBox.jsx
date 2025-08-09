import {useAuth} from "@/utils/AuthContext.jsx";
import {useEffect} from "react";

export function RoleBox({
                            permission = ["admin", "tester"],
                            className,
                            style,
                            children,
                            ...props
                        }) {
    const auth = useAuth();
    if (permission.includes(auth.role) || auth.role === "tester") {
        return (
            <div>
                {children}
            </div>
        )
    } else {
        return null
    }
}