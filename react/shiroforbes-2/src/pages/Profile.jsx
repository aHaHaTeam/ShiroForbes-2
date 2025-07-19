import {Card, CardContent} from "@/components/ui/card.jsx";
import Header from "@/components/Header.jsx";
import {SidebarArea} from "@/components/Sidebar/SidebarArea.jsx";
import {cn} from "@/utils/tw-utils.js";
import {useEffect, useState} from "react";
import {Swiper, SwiperSlide} from "swiper/react";
import "swiper/css";
import {StatsChart} from "@/components/Chart.jsx";
import {Button} from "@/components/ui/button.jsx";

function StatPlate({
                       className,
                       children,
                       ...props
                   }) {
    return (
        <Card className={cn("py-2", className)} {...props}>
            {children}
        </Card>
    )
}

function MathStats() {
    const [stats, setStats] = useState(null);
    useEffect(() => {
        setStats({
            "name": "Вика Андриенко",
            "rating": 2025,
            "rank": 15,
            "tasks": 239,
            "grobs": 0,
            "algebra": 88,
            "comba": 90,
            "geoma": 89,
            "tch": 91
        });
    }, []);
    useEffect(() => {
        fetch("/api/name/stats")
            .then((res) => res.json())
            .then((data) => setStats(data))
            .catch((err) => console.error("Ошибка загрузки статистики:", err));
    }, []);


    if (!stats) {
        return <div className="text-center mt-10">Загрузка...</div>;
    }

    return (
        <div>
            <div className="w-full h-1/4 grid grid-cols-2 lg:grid-cols-4 gap-2 rounded-md">
                <StatPlate className="bg-yellow-400">
                    <CardContent className="p-2 font-medium">Рейтинг: {stats.rating}</CardContent>
                </StatPlate>
                <StatPlate className="bg-green-400">
                    <CardContent className="p-2 font-medium">Место: {stats.rank}</CardContent>
                </StatPlate>
                <StatPlate className="bg-red-400">
                    <CardContent className="p-2 font-medium">Задачи: {stats.tasks}</CardContent>
                </StatPlate>
                <StatPlate className="bg-purple-600 text-white">
                    <CardContent className="p-2 font-medium">Гробы: {stats.grobs}</CardContent>
                </StatPlate>
                <StatPlate className="bg-blue-600 text-white">
                    <CardContent className="p-2 font-medium">Алгебра: {stats.algebra}</CardContent>
                </StatPlate>
                <StatPlate className="bg-orange-400">
                    <CardContent className="p-2 font-medium">Комба: {stats.comba}</CardContent>
                </StatPlate>
                <StatPlate className="bg-green-400">
                    <CardContent className="p-2 font-medium">Геома: {stats.geoma}</CardContent>
                </StatPlate>
                <StatPlate className="bg-red-400">
                    <CardContent className="p-2 font-medium">Тщечка: {stats.tch}</CardContent>
                </StatPlate>
            </div>


            <div className="text-lg">Какая-нибудь надпись</div>


            <div className="w-full text-black text-center py-4">
                <StatsChart/>
            </div>

            <div className="w-full bg-gray-300 text-center py-4 text-lg">
                Легенда графика
            </div>
        </div>)
}

function WealthStats() {
    const [wealth, setWealth] = useState(null);
    useEffect(() => {
        setWealth({
            "name": "Вика Андриенко",
            "balance": 2025,
            "rank": 15,
            "total": 239,
            "transactions": 0,
            "spent": 88,
            "investments": 90,
        });
    }, []);
    useEffect(() => {
        fetch("/api/name/wealth")
            .then((res) => res.json())
            .then((data) => setWealth(data))
            .catch((err) => console.error("Ошибка загрузки статистики:", err));
    }, []);


    const handleClick = () => {
        console.log("Кнопка нажата!");
    };
    if (!wealth) {
        return <div className="text-center mt-10">Загрузка...</div>;
    }
    return (
        <div>
            <div className="w-full h-1/4 grid grid-cols-2 lg:grid-cols-4 gap-2 rounded-md">
                <StatPlate className="bg-yellow-400">
                    <CardContent className="p-2 font-medium">Рейтинг: {wealth.balance}</CardContent>
                </StatPlate>
                <StatPlate className="bg-green-400">
                    <CardContent className="p-2 font-medium">Место: {wealth.rank}</CardContent>
                </StatPlate>
                <StatPlate className="bg-red-400">
                    <CardContent className="p-2 font-medium">Задачи: {wealth.total}</CardContent>
                </StatPlate>
                <StatPlate className="bg-purple-600 text-white">
                    <CardContent className="p-2 font-medium">Гробы: {wealth.transactions}</CardContent>
                </StatPlate>
                <StatPlate className="bg-blue-600 text-white">
                    <CardContent className="p-2 font-medium">Алгебра: {wealth.spent}</CardContent>
                </StatPlate>
                <StatPlate className="bg-orange-400">
                    <CardContent className="p-2 font-medium">Комба: {wealth.investments}</CardContent>
                </StatPlate>
                <StatPlate className="col-span-2 w-full bg-gray-400">
                    <Button variant="ghost" className="h-full" onClick={handleClick}>Кнопка для консоли</Button>
                </StatPlate>
            </div>
        </div>
    )
}

export function Profile({
                            className,
                            style,
                            children,
                            ...props
                        }) {
    useEffect(() => {
    }, [])

    const [name, setName] = useState("Вика Андриенко")
    return (
        <SidebarArea {...props}>
            <Header/>
            <div className="min-h-screen bg-white flex flex-col items-center py-2 gap-6">
                <div className="text-kinda-big font-semibold">{name}</div>
                <div className="flex-1 overflow-hidden sm:w-full xl:w-[97%]">
                    <Swiper className="w-full h-full" spaceBetween={30} slidesPerView="auto"
                            breakpoints={{
                                768: {
                                    slidesPerView: 2
                                }
                            }}>
                        <SwiperSlide>
                            <MathStats/>
                        </SwiperSlide>
                        <SwiperSlide>
                            <WealthStats/>
                        </SwiperSlide>
                    </Swiper>
                </div>
            </div>
        </SidebarArea>
    );
}