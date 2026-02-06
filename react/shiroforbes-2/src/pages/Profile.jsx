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
import {useParams} from "react-router-dom";
import {AchievementsGrid} from "@/components/AchievementsGrid.jsx";

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
        {color: "bg-gray-100 border border-gray-300", text: "Баллы", key: "totalRating"},
        {color: "bg-gray-100 border border-gray-300", text: "Место", key: "position"},

        {color: "bg-red-300", text: "Задачи", key: "totalSolved"},
        {color: "bg-purple-300", text: "Гробы", key: "grobs"},

        {color: "bg-blue-300", text: "Алгебра", key: "algebraSolvedPercent"},
        {color: "bg-orange-200", text: "Комба", key: "combinatoricsSolvedPercent"},
        {color: "bg-green-300", text: "Геома", key: "geometrySolvedPercent"},
        {color: "bg-yellow-200", text: "Тчшечка", key: "numbersTheorySolvedPercent"},
    ]


    useEffect(() => {
        console.log("math history ", history);
    }, [history]);

    return (
        <div>
            <div className="row-auto">
                <p>Последняя учтённая серия: </p>{stats ? stats["episode"] :
                <Skeleton className="h-5 w-20 rounded bg-gray-200"/>}
            </div>
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
                                    <p>{text}:</p>
                                    <Skeleton className="h-5 w-20 rounded bg-gray-200"/>
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


export function Profile({
                            className,
                            style,
                            children,
                            ...props
                        }) {
    const {username} = useParams();
    const [mathStats, setMathStats] = useState(null);
    const [achievements, setAchievements] = useState(null);
    const [name, setName] = useState("")
    const [fetchedData, setFetchedData] = useState([]);
    const apiFetch = useApiFetch();
    const userData = useData();
    useEffect(() => {
        if (username === undefined) {
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
            setAchievements(data.achievements);
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
                        {achievements && <SwiperSlide className="h-max">
                            <AchievementsGrid items={achievements}/>
                        </SwiperSlide>}
                    </Swiper>
                </div>
            </div>
        </SidebarArea>
    );
}