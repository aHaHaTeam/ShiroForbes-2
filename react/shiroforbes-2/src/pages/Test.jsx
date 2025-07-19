import {SidebarArea} from "@/components/Sidebar/SidebarArea.jsx";
import Header from "@/components/Header.jsx";
import {StatsChart} from "@/components/Chart.jsx";

export function Test({
                         className,
                         style,
                         children,
                         ...props
                     }) {
    return (
        <div className={className} {...props}>
            <SidebarArea>
                <Header/>
                <StatsChart className="lg:w-1/2 w-full"/>
                {children}
            </SidebarArea>
        </div>
    )
}