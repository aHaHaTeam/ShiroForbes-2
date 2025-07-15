import {SidebarProvider} from "@/components/ui/sidebar.jsx";
import {AppSidebar} from "@/components/Sidebar/AppSidebar.jsx";
import {useIsMobile} from "@/hooks/use-mobile.js";

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
        <SidebarProvider defaultOpen={defaultOpen} open={openProp} onOpenChange={setOpenProp} className={className} style={style} {...props}>
            <AppSidebar collapsible={useIsMobile()?"offcanvas":"icon"}/>
            {children}
        </SidebarProvider>
    )

}