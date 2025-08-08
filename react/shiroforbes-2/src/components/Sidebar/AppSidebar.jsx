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
import {DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuTrigger} from "@radix-ui/react-dropdown-menu";
import {Button} from "@/components/ui/button.jsx";
import {useData} from "@/utils/DataContext.jsx";

const items = [
    {
        title: "Test Page",
        url: "test",
        icon: Settings,
    },
    {
        title: "Раздать денег",
        url: "money-distribution",
        icon: Coins,
    },
    {
        title: "Профиль",
        url: "profile",
        icon: User,
    },
    {
        title: "Рейтинг",
        url: "rating",
        icon: Medal,
    },
    {
        title: "Прайс-лист",
        url: "shop",
        icon: ShoppingCart,
    },
    {
        title: "Список транзакций",
        url: "transactions",
        icon: ArrowLeftRight,
    },
    {
        title: "Перезайти",
        url: "login",
        icon: LogOut,
    },
]

const campVariants = [
    "Загородный",
    "Городской1",
    "Городской2"
]

export function AppSidebar({collapsible = "offcanvas", ...props}) {
    const userData = useData();
    return (
        <Sidebar collapsible={collapsible} variant="" {...props} className="pt-16">
            <SidebarContent>
                <SidebarGroup>
                    <SidebarGroupLabel>
                        Application
                    </SidebarGroupLabel>
                    <SidebarGroupContent>

                        <SidebarMenu>
                            {items.map((item) => (
                                <SidebarMenuItem key={item.title}>
                                    <SidebarMenuButton asChild>
                                        <a href={item.url}>
                                            <item.icon/>
                                            <span>{item.title}</span>
                                        </a>
                                    </SidebarMenuButton>
                                </SidebarMenuItem>
                            ))}
                        </SidebarMenu>
                    </SidebarGroupContent>
                </SidebarGroup>
                <SidebarGroup>
                    <SidebarGroupLabel>
                        Варианты лагеря
                    </SidebarGroupLabel>
                    <SidebarGroupContent>
                        <SidebarMenu>
                            {
                                campVariants.map((item) => (
                                    <SidebarMenuItem>
                                        <Button variant="ghost"
                                                className="w-full text-left border-0 bg-sidebar shadow-sidebar"
                                                onClick={() => {
                                                    userData.setCamp({camp: item});
                                                    console.log(item);
                                                }}>
                                            <p className="w-full">{item}</p>
                                        </Button>
                                    </SidebarMenuItem>
                                ))
                            }
                        </SidebarMenu>
                    </SidebarGroupContent>
                </SidebarGroup>
            </SidebarContent>
        </Sidebar>
    )
}