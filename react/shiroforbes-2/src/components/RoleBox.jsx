import {useAuth} from "@/utils/AuthContext.jsx";

export function RoleBox({
                            permission = ["admin", "tester", "teacher"],
                            banned = [],
                            className,
                            style,
                            children,
                            ...props
                        }) {
    const auth = useAuth();
    if (permission.includes(auth.role.toLowerCase())) {
        return (
            <div className={className} style={style} {...props}>
                {children}
            </div>
        )
    } else if (banned.includes(auth.role.toLowerCase())) {
        return (<div><span>Сюда подглядывать нельзя</span>
        <img alt={"Картинка с котом"} src={"src/assets/silly-cat-bleh.jpg"} className="w-full"/></div>)
    } else {
        return null
    }
}