import {SidebarArea} from "@/components/Sidebar/SidebarArea.jsx";
import Header from "@/components/Header.jsx";

import {RatingTable} from "@/components/RatingTable.jsx";


export function Rating({
                           className,
                           style,
                           children,
                           ...props
                       }) {
    return (
        <div className={className} {...props}>
            <SidebarArea>
                <Header/>
                <div className="flex-1 overflow-hidden sm:w-full xl:w-[97%]">
                    <RatingTable/>
                </div>
            </SidebarArea>
        </div>
    )
}