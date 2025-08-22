"use client"
import {CartesianGrid, Legend, Line, LineChart, XAxis, YAxis} from "recharts"

import {
    Card,
    CardContent,
} from "@/components/ui/card"

import {
    ChartContainer,
    ChartTooltip,
    ChartTooltipContent,
} from "@/components/ui/chart"
import {useData} from "@/utils/DataContext.jsx";
import {useApiFetch} from "@/utils/api.js";
import {useEffect, useState} from "react";


const chartConfig = {
    desktop: {
        label: "Место",
        color: "var(--chart-1)",
    },
    mobile: {
        label: "Очки",
        color: "var(--chart-2)",
    },
}

export function StatsChart({
                               history, className,
                               style,
                               children,
                               ...props
                           }) {


    const [chartData, setChartData] = useState(history);

    useEffect(() => {
        console.log("chart", history);
        setChartData(history);
    }, [history]);

    if (history === null) {
        return (<>
                </>)
    }
    return (
        <div className={className} {...props}>
            <Card className="pb-0">
                <CardContent className="px-0">
                    <ChartContainer config={chartConfig} className="w-full">
                        <LineChart
                            accessibilityLayer
                            data={chartData}
                            margin={{
                                left: -24,
                                right: -16,
                            }}
                        >
                            <CartesianGrid vertical={false}/>
                            <XAxis
                                dataKey="episode"
                                tickLine={false}
                                axisLine={true}
                                tickMargin={8}
                                // tickFormatter={(value) => value.slice(0, 3)}
                            />
                            <YAxis
                                yAxisId="rank"
                                reversed={true}
                                tickLine={false}
                                axisLine={false}
                                tickMargin={8}
                                domain={[0, 36]}
                            />

                            <YAxis
                                yAxisId="solved"
                                orientation="right"
                                tickLine={false}
                                axisLine={false}
                                tickMargin={8}
                                domain={[0, 100]}
                            />
                            <ChartTooltip cursor={false} content={<ChartTooltipContent/>}/>
                            <Legend
                                verticalAlign="bottom"
                                align="center"
                                height={32}
                                iconType="line"
                                formatter={(value) => {
                                    if (value === "solved") return "Solved";
                                    if (value === "rank") return "Rank";
                                    return value;
                                }}
                            />
                            <Line
                                dataKey="totalSolved"
                                yAxisId="solved"
                                type="linear"
                                stroke="var(--color-geometry-green)"
                                strokeWidth={2}
                                dot={true}
                            />
                            <Line
                                dataKey="position"
                                yAxisId="rank"
                                type="linear"
                                stroke="var(--color-algebra-blue)"
                                strokeWidth={2}
                                dot={true}
                            />
                        </LineChart>
                    </ChartContainer>
                </CardContent>
            </Card>
        </div>
    )
}
