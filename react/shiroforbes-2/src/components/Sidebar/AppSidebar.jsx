import {ArrowLeftRight, Coins, LogOut, Medal, Settings, ShoppingCart, User} from "lucide-react"

import {
    Sidebar,
    SidebarContent,
    SidebarGroup,
    SidebarGroupContent,
    SidebarGroupLabel,
    SidebarMenu,
    SidebarMenuButton,
    SidebarMenuItem,
} from "@/components/ui/sidebar"
import {Button} from "@/components/ui/button.jsx";
import {useData} from "@/utils/DataContext.jsx";
import {RoleBox} from "@/components/RoleBox.jsx";
import {toast} from "sonner";

const items = [
    {
        title: "Test Page",
        url: "/test",
        icon: Settings,
        roles: []
    },
    // {
    //     title: "Раздать денег",
    //     url: "money-distribution",
    //     icon: Coins,
    //     roles: ["admin"]
    //
    // },
    {
        title: "Профиль",
        url: "/profile/",
        icon: User,
        roles: ["student"]
    },
    {
        title: "Рейтинг",
        url: "/rating",
        icon: Medal,
        roles: ["admin", "student", "stranger"]
    },
    // {
    //     title: "Прайс-лист",
    //     url: "shop",
    //     icon: ShoppingCart,
    //     roles: ["admin", "student"]
    //
    // },
    // {
    //     title: "Список транзакций",
    //     url: "transactions",
    //     icon: ArrowLeftRight,
    //     roles: ["admin"]
    // },
    {
        title: "Перезайти",
        url: "/login",
        icon: LogOut,
        roles: ["admin", "student", "stranger"]
    },
]

const campVariants = [
    {title: "Загородный", url: "Countryside"},
    {title: "Городской1", url: "Urban1"},
    {title: "Городской2", url: "Urban2"}
]

export function AppSidebar({collapsible = "offcanvas", ...props}) {
    const userData = useData();
    return (
        <Sidebar collapsible={collapsible} variant="" {...props} className="pt-16">
            <SidebarContent>
                <SidebarGroup>
                    <SidebarGroupLabel>
                        Странички
                    </SidebarGroupLabel>
                    <SidebarGroupContent>
                        <SidebarMenu>
                            {items.map((item, index) => (
                                <RoleBox permission={item.roles} key={item.title}>
                                    <SidebarMenuItem>
                                        <SidebarMenuButton asChild>
                                            <a href={item.url}>
                                                <item.icon/>
                                                <span>{item.title}</span>
                                            </a>
                                        </SidebarMenuButton>
                                    </SidebarMenuItem>
                                </RoleBox>
                            ))}
                        </SidebarMenu>
                    </SidebarGroupContent>
                </SidebarGroup>
                <RoleBox>
                    <SidebarGroup>
                        <SidebarGroupLabel>
                            Варианты лагеря
                        </SidebarGroupLabel>
                        <SidebarGroupContent>
                            <SidebarMenu>
                                {campVariants.map((item, index) => (
                                    <SidebarMenuItem key={item.url}>
                                        <Button variant="ghost"
                                                className="w-full text-left border-0 bg-sidebar shadow-sidebar"
                                                onClick={() => {
                                                    userData.setCamp({camp: item.url});
                                                    toast(`Переключено на ${item.title}`);
                                                }}>
                                            <p className="w-full">{item.title}</p>
                                        </Button>
                                    </SidebarMenuItem>
                                ))
                                }
                            </SidebarMenu>
                        </SidebarGroupContent>
                    </SidebarGroup>
                </RoleBox>
            </SidebarContent>
        </Sidebar>
    )
}