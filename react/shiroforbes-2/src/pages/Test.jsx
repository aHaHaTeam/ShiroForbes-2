import {SidebarArea} from "@/components/Sidebar/SidebarArea.jsx";
import Header from "@/components/Header.jsx";
import {TransactionGroupTable} from "@/components/TransactionsTable.jsx";

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
                <TransactionGroupTable/>
                {children}
            </SidebarArea>
        </div>
    )
}