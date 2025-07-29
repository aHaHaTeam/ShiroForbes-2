import {SidebarArea} from "@/components/Sidebar/SidebarArea.jsx";
import Header from "@/components/Header.jsx";
import {RatingTable} from "@/components/RatingTable.jsx";
import {MoneyDistribution} from "@/pages/MoneyDistribution.jsx";

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
                <MoneyDistribution/>
                {children}
            </SidebarArea>
        </div>
    )
}