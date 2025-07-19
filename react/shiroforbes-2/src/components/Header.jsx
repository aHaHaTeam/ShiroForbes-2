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
                    <a href="/profile" className="text-gray-700 dark:text-gray-200 hover:text-brandOrange transition">Link 1</a>
                </nav>
            </div>
        </header>
    );
}
