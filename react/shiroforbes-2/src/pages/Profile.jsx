import {Card, CardContent} from "@/components/ui/card.jsx";
import Header from "@/components/Header.jsx";
import {SidebarArea} from "@/components/Sidebar/SidebarArea.jsx";
import {cn} from "@/utils/tw-utils.js";
import {useEffect, useState} from "react";
import {Swiper, SwiperSlide} from "swiper/react";
import "swiper/css";
import {StatsChart} from "@/components/Chart.jsx";
import {useApiFetch} from "@/utils/api.js";
import {Skeleton} from "@/components/ui/skeleton.jsx";
import {useData} from "@/utils/DataContext.jsx";
import {TransactionProfileTable} from "@/components/TransactionsTable.jsx";
import {Checkbox} from "@/components/ui/checkbox.jsx";

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

function MathStats({stats}) {


    if (!stats) {
        return (
            <div>
                <div className="w-full h-1/4 grid grid-cols-2 lg:grid-cols-4 gap-2 rounded-md">
                    {Array.from({length: 8}).map((_, i) => (
                        <div key={i} className="p-2">
                            <Skeleton className="h-16 w-full rounded-md"/>
                        </div>
                    ))}
                </div>

                <div className="text-lg py-4">
                    <Skeleton className="h-6 w-1/2 mx-auto"/>
                </div>

                <div className="w-full text-black text-center py-4">
                    <Skeleton className="h-64 w-full rounded-md"/>
                </div>

                <div className="w-full text-center py-4">
                    <Skeleton className="h-6 w-1/3 mx-auto"/>
                </div>
            </div>
        );
    }

    return (
        <div>
            <div className="w-full h-1/4 grid grid-cols-2 lg:grid-cols-4 gap-2 rounded-md">
                <StatPlate className="bg-yellow-400">
                    <CardContent className="p-2 font-medium">Рейтинг: {stats.rating}</CardContent>
                </StatPlate>
                <StatPlate className="bg-green-400">
                    <CardContent className="p-2 font-medium">Место: {stats.wealthRank}</CardContent>
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
                    <CardContent className="p-2 font-medium">Тчшечка: {stats.tch}</CardContent>
                </StatPlate>
            </div>


            <div className="w-full text-black text-center py-4">
                <StatsChart/>
            </div>

            <div className="w-full bg-gray-300 text-center py-4 text-lg">
                Легенда графика
            </div>
        </div>)
}

function WealthStats({stats}) {
    const apiFetch = useApiFetch();
    const userData = useData();
    const [invest, setInvest] = useState(true);

    const handleClick = () => {
        setInvest(!invest);
        apiFetch(`/api/${userData.username}/invest`, {
            method: "POST",
            body: JSON.stringify({
                isInvesting: invest
            })
        }).then(console.log);
    };

    useEffect(() => {
        if (stats) {
            setInvest(stats.isInvesting)
        }
    }, [stats]);

    if (!stats) {
        return <div className="text-center mt-10">Загрузка...</div>;
    }
    return (
        <div>
            <div className="w-full h-1/4 grid grid-cols-2 lg:grid-cols-4 gap-2 rounded-md">
                <StatPlate className="bg-yellow-400">
                    <CardContent className="p-2 font-medium">Баланс: {stats.balance}</CardContent>
                </StatPlate>
                <StatPlate className="bg-green-400">
                    <CardContent className="p-2 font-medium">Место: {stats.wealthRank}</CardContent>
                </StatPlate>
                <StatPlate className="bg-red-400">
                    <CardContent className="p-2 font-medium">Заработано: {stats.total}</CardContent>
                </StatPlate>
                <StatPlate className="bg-purple-600 text-white">
                    <CardContent className="p-2 font-medium">Транзакций: {stats.transactions}</CardContent>
                </StatPlate>
                <StatPlate className="bg-blue-600 text-white">
                    <CardContent className="p-2 font-medium">Потрачено: {stats.spent}</CardContent>
                </StatPlate>
                <StatPlate className="bg-orange-400">
                    <CardContent className="p-2 font-medium">Инвестиции: {stats.investments}</CardContent>
                </StatPlate>
                <div className="col-span-2 bg-gray-400 rounded p-2 flex items-center justify-between gap-4 mb-6">
                    <div>Инвестирую</div>
                    <Checkbox checked={invest} onCheckedChange={handleClick} />
                </div>

            </div>
            <TransactionProfileTable className="p-4"/>
        </div>
    )
}

export function Profile({
                            className,
                            style,
                            children,
                            ...props
                        }) {

    const [mathStats, setMathStats] = useState(null);
    const [wealthStats, setWealthStats] = useState(null)
    const [name, setName] = useState("")

    const userData = useData();
    const apiFetch = useApiFetch();

    useEffect(() => {
        apiFetch(`/api/${userData.username}/profile`)
            .then((res) => {
                if (!res.ok) {
                    throw new Error(`HTTP ${res.status}`);
                }
                return res.json();
            }).then((data) => {
            setMathStats(data.mathStats);
            setWealthStats(data.wealthStats);
            setName(data.name);
        })
            .catch((err) => console.error("Ошибка загрузки статистики:", err));
    }, []);

    return (
        <SidebarArea {...props}>
            <Header/>
            <div className="min-h-screen bg-white flex flex-col items-center py-2 gap-6">
                <div className="text-kinda-big font-semibold">{name}</div>
                <div className="flex-1 overflow-hidden w-[97%]">
                    <Swiper className="w-full h-full" spaceBetween={30} slidesPerView="auto"
                            breakpoints={{
                                768: {
                                    slidesPerView: 2
                                }
                            }}>
                        <SwiperSlide>
                            <MathStats stats={mathStats}/>
                        </SwiperSlide>
                        <SwiperSlide>
                            <WealthStats stats={wealthStats}/>
                        </SwiperSlide>
                    </Swiper>
                </div>
            </div>
        </SidebarArea>
    );
}