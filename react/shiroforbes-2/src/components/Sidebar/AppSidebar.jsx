import {Calendar, Coins, Home, Inbox, LogOut, Medal, Search, Settings, User} from "lucide-react"

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

// Menu items.
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
        title: "Перезайти",
        url: "login",
        icon: LogOut,
    },
]

export function AppSidebar({collapsible = "offcanvas", ...props}) {
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
            </SidebarContent>
        </Sidebar>
    )
}