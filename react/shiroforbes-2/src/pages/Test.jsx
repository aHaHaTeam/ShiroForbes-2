import {SidebarArea} from "@/components/Sidebar/SidebarArea.jsx";
import Header from "@/components/Header.jsx";
import {TransactionGroupTable} from "@/components/TransactionsTable.jsx";
import {Achievement} from "@/components/Achievement.jsx";
import {AchievementsGrid} from "@/components/AchievementsGrid.jsx";


let items = [
    {
        title: "aaaa",
        pictureUrl: "https://shiroforbes.ru/grob2.png",
        description: "bebebebebebebebbe",
        date: "15.05.1997"
    },
    {
        title: "aaaa",
        pictureUrl: "https://shiroforbes.ru/grob2.png",
        description: "bebebebebebebebbe",
        date: "15.05.1997"
    }, {
        title: "aaaa",
        pictureUrl: "https://shiroforbes.ru/grob2.png",
        description: "bebebebebebebebbe",
        date: "15.05.1997"
    }, {
        title: "aaaa",
        pictureUrl: "https://shiroforbes.ru/grob2.png",
        description: "bebebebebebebebbe",
        date: "15.05.1997"
    },
    {title: "aaaa",
        pictureUrl: "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%2Fid%2FOIP.7l37Dp8rJzwy1kY4RLlV4gHaH6%3Fpid%3DApi&f=1&ipt=ede4ee227771166b2762dade6c76241bdcd34d5bcf83093e49762b405ef7708f&ipo=images",
        description: "bebebebebebebebbe",
        date: "15.05.1997"},
    {title: "aaaa",
        pictureUrl: "https://shiroforbes.ru/grob2.png",
        description: "bebebebebebebebbe",
        date: "15.05.1997"},
    {title: "aaaa",
        pictureUrl: "https://shiroforbes.ru/grob2.png",
        description: "bebebebebebebebbe",
        date: "15.05.1997"},

]

export function Test({
                         className,
                         style,
                         children,
                         ...props
                     }) {
    return (
        <div className={className} style={style} {...props}>
            <SidebarArea>
                <Header/>
                <AchievementsGrid items={items}/>
                {children}
            </SidebarArea>
        </div>
    )
}