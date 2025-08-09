import {SidebarProvider} from "@/components/ui/sidebar.jsx";
import {AppSidebar} from "@/components/Sidebar/AppSidebar.jsx";
import {useIsMobile} from "@/hooks/use-mobile.js";
import {Toaster} from "@/components/ui/sonner.jsx";

export function SidebarArea({
                                defaultOpen = true,
                                open: openProp,
                                onOpenChange: setOpenProp,
                                className,
                                style,
                                children,
                                ...props
                            }) {
    return (
        <SidebarProvider defaultOpen={defaultOpen} open={openProp} onOpenChange={setOpenProp} className={className}
                         style={style} {...props}>
            <AppSidebar collapsible={useIsMobile() ? "offcanvas" : "icon"}/>
            <Toaster/>
            <main className="pt-16 w-full">
                {children}
            </main>
        </SidebarProvider>
    )

}