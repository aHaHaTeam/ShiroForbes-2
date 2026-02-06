// src/components/Header.jsx
import Logo from "@/components/Logo/Logo.jsx";
import {SidebarTrigger} from "@/components/ui/sidebar.jsx";

export default function Header() {
    return (
        <header className="fixed top-0 left-0 w-full bg-white dark:bg-zinc-900 shadow z-50">
            <div className="max-w-screen-xl mx-auto px-4 sm:px-6 lg:px-8 h-16 flex items-center justify-between">
                <SidebarTrigger/>
                <Logo className="text-kinda-big"/>
                <nav className="space-x-4">
                    <a href="https://www.overleaf.com/project/692b44a92a13f755f859c594" className="w-1">
                        <img alt={"Гроб"} src={"/grob2.png"} className="w-7 h-auto"></img>
                    </a>
                </nav>
            </div>
        </header>
    );
}
