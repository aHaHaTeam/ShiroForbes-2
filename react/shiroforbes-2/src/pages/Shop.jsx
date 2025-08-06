import {SidebarArea} from "@/components/Sidebar/SidebarArea.jsx";
import Header from "@/components/Header.jsx";
import {ShopTable} from "@/components/ShopTable.jsx";

export function Shop({
                         className,
                         style,
                         children,
                         ...props
                     }) {
    return (
        <div className={className} {...props}>
            <SidebarArea>
                <Header/>
                <ShopTable/>
                {children}
            </SidebarArea>
        </div>
    )
}