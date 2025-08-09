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
import {toast} from "sonner";

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

    const plates = [
        {color: "bg-yellow-400", text: "Баллы", key: "rating"},
        {color: "bg-green-400", text: "Место", key: "wealthRank"},
        {color: "bg-red-400", text: "Задачи", key: "tasks"},
        {color: "bg-purple-600 text-white", text: "Гробы", key: "grobs"},
        {color: "bg-blue-600 text-white", text: "Алгебра", key: "algebra"},
        {color: "bg-orange-400", text: "Комба", key: "comba"},
        {color: "bg-green-400", text: "Геома", key: "geoma"},
        {color: "bg-red-400", text: "Тчшечка", key: "tch"},
    ]


    return (
        <div>
            <div className="w-full h-1/4 grid grid-cols-2 lg:grid-cols-4 gap-2 rounded-md">
                {plates.map(({color, text, key}) => (
                    <StatPlate key={key} className={color}>
                        <CardContent className="p-2 font-medium">
                            {stats ? (
                                <>
                                    {text}: {stats[key]}
                                </>
                            ) : (
                                <div className="flex items-center gap-2">
                                    {text}: <Skeleton className="h-5 w-20 rounded bg-gray-200"/>
                                </div>
                            )}
                        </CardContent>
                    </StatPlate>
                ))}
            </div>
            <div className="w-full text-black text-center py-4">
                <StatsChart/>
            </div>
        </div>
    )
}

function WealthStats({stats}) {
    const apiFetch = useApiFetch();
    const userData = useData();
    const [invest, setInvest] = useState(true);

    const handleClick = () => {
        const newInvest = !invest;
        setInvest(newInvest);
        try {
            apiFetch(`/api/${userData.username}/invest`, {
                method: "POST",
                body: JSON.stringify({
                    isInvesting: newInvest
                })
            }).then(() => toast("обновлено"));
        } catch (err) {
            console.log(err);
            toast(`Ошибка подключения: ${err}`);
        }
    };

    useEffect(() => {
        if (stats) {
            setInvest(stats.isInvesting)
        }
    }, [stats]);

    const plates = [
        {color: "bg-yellow-400", text: "Баланс", key: "balance"},
        {color: "bg-green-400", text: "Место", key: "wealthRank"},
        {color: "bg-red-400", text: "Всего", key: "total"},
        {color: "bg-purple-600 text-white", text: "Транши", key: "transactions"},
        {color: "bg-blue-600 text-white", text: "Потрачено", key: "spent"},
        {color: "bg-orange-400", text: "Инвестиции", key: "investments"},
    ]

    return (
        <div>
            <div className="w-full h-1/4 grid grid-cols-2 lg:grid-cols-4 gap-2 rounded-md">
                {plates.map(({color, text, key}) => (
                    <StatPlate key={key} className={color}>
                        <CardContent className="p-2 font-medium">
                            {stats ? (
                                <>
                                    {text}: {stats[key]}
                                </>
                            ) : (
                                <div className="flex items-center gap-2">
                                    {text}:
                                    <Skeleton className="h-5 w-20 rounded bg-gray-200"/>
                                </div>
                            )}
                        </CardContent>
                    </StatPlate>
                ))}

                <div
                    className="py-4 col-span-2 bg-gray-400 rounded-lg p-2 flex items-center justify-between gap-4 mb-6">
                    <div>Инвестирую</div>
                    {stats ? (
                        <Checkbox checked={invest} onCheckedChange={handleClick}/>
                    ) : (
                        <Skeleton className="h-5 w-5 rounded bg-gray-200"/>
                    )}
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
        const url = `/api/${userData.username}/profile`;
        apiFetch(url)
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
            .catch((err) => console.error(`Ошибка загрузки ${url}`, err));
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