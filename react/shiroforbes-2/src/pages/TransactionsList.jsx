import {SidebarArea} from "@/components/Sidebar/SidebarArea.jsx";
import Header from "@/components/Header.jsx";
import {TransactionGroupTable} from "@/components/TransactionsTable.jsx";

export function TransactionsList({
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
                    <TransactionGroupTable/>
                </div>
            </SidebarArea>
        </div>
    )
}