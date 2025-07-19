"use client"

import {TrendingDown} from "lucide-react"
import {CartesianGrid, Line, LineChart, XAxis, YAxis} from "recharts"

import {
    Card,
    CardContent,
    CardFooter,
} from "@/components/ui/card"

import {
    ChartContainer,
    ChartTooltip,
    ChartTooltipContent,
} from "@/components/ui/chart"

const chartData = [
    {month: "January", desktop: 186, mobile: 80},
    {month: "February", desktop: 305, mobile: 200},
    {month: "March", desktop: 237, mobile: 120},
    {month: "April", desktop: 73, mobile: 190},
    {month: "May", desktop: 209, mobile: 130},
    {month: "June", desktop: 214, mobile: 140},
]

const chartConfig = {
    desktop: {
        label: "Desktop",
        color: "var(--chart-1)",
    },
    mobile: {
        label: "Mobile",
        color: "var(--chart-2)",
    },
}

export function StatsChart({className,
                                      style,
                                      children,
                                      ...props}) {
    return (
        <div className={className} {...props}>
            <Card>

                <CardContent>
                    <ChartContainer config={chartConfig}>
                        <LineChart
                            accessibilityLayer
                            data={chartData}
                            margin={{
                                left: -24,
                                right: 12,
                            }}
                        >
                            <CartesianGrid vertical={false}/>
                            <XAxis
                                dataKey="month"
                                tickLine={false}
                                axisLine={true}
                                tickMargin={8}
                                tickFormatter={(value) => value.slice(0, 3)}
                            />
                            <YAxis
                                tickLine={false}
                                axisLine={false}
                                tickMargin={8}
                                tickCount={10}
                            />
                            <ChartTooltip cursor={false} content={<ChartTooltipContent/>}/>
                            <Line
                                dataKey="desktop"
                                type="linear"
                                stroke="var(--color-geometry-green)"
                                strokeWidth={2}
                                dot={false}
                            />
                            <Line
                                dataKey="mobile"
                                type="linear"
                                stroke="var(--color-algebra-blue)"
                                strokeWidth={2}
                                dot={false}
                            />
                        </LineChart>
                    </ChartContainer>
                </CardContent>
                <CardFooter>
                    <div className="flex w-full  items-start gap-2 text-sm">
                        <div className="grid gap-2">
                            <div className="flex items-center gap-2 leading-none font-medium">
                                This Chart took me 6 hours <TrendingDown className="h-4 w-4"/>
                            </div>
                            <div className="text-muted-foreground flex items-center gap-2 leading-none">
                                (^_^)
                            </div>
                        </div>
                    </div>
                </CardFooter>
            </Card>
        </div>
    )
}
