import {useAuth} from "@/utils/AuthContext.jsx";

export function RoleBox({
                            permission = ["admin", "tester"],
                            className,
                            style,
                            children,
                            ...props
                        }) {
    const auth = useAuth();
    if (permission.includes(auth.role.toLowerCase()) || auth.role === "tester") {
        return (
            <div className={className} style={style} {...props}>
                {children}
            </div>
        )
    } else {
        return null
    }
}