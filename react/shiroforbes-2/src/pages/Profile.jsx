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
import path from "path";
import {useParams} from "react-router-dom";

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

function MathStats({stats, history}) {

    const plates = [
        { color: "bg-gray-100 border border-gray-300", text: "Баллы", key: "totalRating" },
        { color: "bg-gray-100 border border-gray-300", text: "Место", key: "position" },

        { color: "bg-red-300", text: "Задачи", key: "totalSolved" },
        { color: "bg-purple-300", text: "Гробы", key: "grobs" },

        { color: "bg-blue-300", text: "Алгебра", key: "algebraSolvedPercent" },
        { color: "bg-orange-200", text: "Комба", key: "combinatoricsSolvedPercent" },
        { color: "bg-green-300", text: "Геома", key: "geometrySolvedPercent" },
        { color: "bg-yellow-200", text: "Тчшечка", key: "numbersTheorySolvedPercent" },
    ]


    useEffect(() => {
        console.log("math history ", history);
    }, [history]);

    return (
        <div>
            <div className="w-full h-1/4 grid grid-cols-2 lg:grid-cols-4 gap-2 rounded-md">
                {plates.map(({color, text, key}) => (
                    <StatPlate key={key} className={color}>
                        <CardContent className="p-2 font-medium">
                            {stats ? (
                                <>
                                    {text}: {Math.round(stats[key] * 10) / 10}
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
                <StatsChart history={history}/>
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
                                <div className="flex items-center gap-2">a
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
    const {username} = useParams();
    const [mathStats, setMathStats] = useState(null);
    const [wealthStats, setWealthStats] = useState(null)
    const [name, setName] = useState("")
    const [fetchedData, setFetchedData] = useState([]);
    const apiFetch = useApiFetch();
    const userData = useData();
    useEffect(() => {
        console.log("-->", username);
        if (username === undefined){
            console.log("trueeeee");
            window.location.href += userData.username;
        }
    }, [])
    useEffect(() => {
        const url = `/api/${username}/profile`;
        apiFetch(url)
            .then((res) => {
                if (!res.ok) {
                    throw new Error(`HTTP ${res.status}`);
                }
                return res.json();
            }).then((data) => {
            console.log(data);
            data.ratings.sort((a, b) => a.episode - b.episode);
            setMathStats(data.ratings[data.ratings.length - 1]);
            setWealthStats(data.wealthStats);
            setName(data.name);
            setFetchedData(data.ratings);
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
                            <MathStats stats={mathStats} history={fetchedData}/>
                        </SwiperSlide>
                        {/*<SwiperSlide>*/}
                        {/*    <WealthStats stats={wealthStats}/>*/}
                        {/*</SwiperSlide>*/}
                    </Swiper>
                </div>
            </div>
        </SidebarArea>
    );
}