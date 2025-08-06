import {SidebarArea} from "@/components/Sidebar/SidebarArea.jsx";
import Header from "@/components/Header.jsx";
import {LoginForm} from "@/components/LoginForm.jsx";

export function LoginPage({
                         className,
                         style,
                         children,
                         ...props
                     }) {
    return (
        <div className={className} {...props}>
            <SidebarArea>
                <Header/>
                <LoginForm/>
                {children}
            </SidebarArea>
        </div>
    )
}