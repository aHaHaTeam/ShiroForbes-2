import {flexRender, getCoreRowModel, useReactTable,} from "@tanstack/react-table"

import {Table, TableBody, TableCell, TableHead, TableHeader, TableRow,} from "@/components/ui/table"
import {useEffect, useState} from "react";
import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuLabel,
    DropdownMenuSeparator,
    DropdownMenuTrigger
} from "@/components/ui/dropdown-menu.jsx"
import {Button} from "@/components/ui/button.jsx";
import {MoreHorizontal} from "lucide-react";

const columns = [
    {
        accessorKey: "place",
        header: "Place",
        cell: ({row}) => {
            const rating = row.original.place;
            const delta = row.original.deltaPlace;
            let deltaElement = null;

            if (delta > 0) {
                deltaElement = <span className="text-green-600">(+{delta})</span>;
            } else if (delta < 0) {
                deltaElement = <span className="text-red-600">({delta})</span>;
            } else {
                deltaElement = <span className="text-gray-400">(=)</span>;
            }

            return (
                <div className="flex justify-center items-center gap-2 text-center">
                    {deltaElement}
                    <span className="text-center">{rating}</span>
                </div>
            );
        },
    },
    {
        accessorKey: "name",
        header: "Name",
    },
    {
        accessorKey: "deltaRating",
        header: "Rating Change",
    },
    {
        accessorKey: "absoluteRating",
        header: "Rating",
    },

]

async function getData({day}) {
    const dataByDay = {
        1: [
            {name: "Alice", absoluteRating: 1580},
            {name: "Bob", absoluteRating: 1540},
            {name: "Charlie", absoluteRating: 152},
        ],
        2: [
            {name: "Bob", absoluteRating: 1590},
            {name: "Alice", absoluteRating: 1565},
            {name: "Charlie", absoluteRating: 152},
        ],
        3: [
            {name: "Charlie", absoluteRating: 160},
            {name: "Bob", absoluteRating: 1560},
            {name: "Alice", absoluteRating: 1555},
        ],
    };

    return dataByDay[day] || [];
}

// Функция сравнения двух рейтингов
async function compareRatings(day1, day2) {
    const oldData = await getData({ day: day1 });
    const newData = await getData({ day: day2 });

    const sortedOld = [...oldData].sort((a, b) => b.absoluteRating - a.absoluteRating);
    const sortedNew = [...newData].sort((a, b) => b.absoluteRating - a.absoluteRating);

    const oldMap = Object.fromEntries(sortedOld.map((item, index) => [item.name, {...item, place: index + 1}]));
    const newMap = Object.fromEntries(sortedNew.map((item, index) => [item.name, {...item, place: index + 1}]));

    const allNames = new Set([...Object.keys(oldMap), ...Object.keys(newMap)]);

    return Array.from(allNames).map((name) => {
        const oldEntry = oldMap[name] || {absoluteRating: 0, place: sortedOld.length + 1};
        const newEntry = newMap[name] || {absoluteRating: 0, place: sortedNew.length + 1};

        return {
            name,
            deltaRating: newEntry.absoluteRating - oldEntry.absoluteRating,
            deltaPlace: oldEntry.place - newEntry.place,
            oldPlace: oldEntry.place,
            newPlace: newEntry.place,
            absoluteRating: newEntry.absoluteRating,
            place: newEntry.place,
        };
    }).sort((a, b) => a.newPlace - b.newPlace);
}

const series = [1,2,3];

export function RatingTable({}) {
    const [data, setData] = useState([])
    const [day1, setDay1] = useState(1);
    const [day2, setDay2] = useState(2);

    useEffect(() => {
        compareRatings(day1, day2).then(setData);
    }, [day1, day2]);

    const table = useReactTable({
        data,
        columns,
        getCoreRowModel: getCoreRowModel(),
    })

    return (
        <div>
            <div className="rounded-md border">
                <Table>
                    <TableHeader>
                        {table.getHeaderGroups().map((headerGroup) => (
                            <TableRow key={headerGroup.id}>
                                {headerGroup.headers.map((header) => {
                                    return (
                                        <TableHead key={header.id} className="text-center">
                                            {header.isPlaceholder
                                                ? null
                                                : flexRender(
                                                    header.column.columnDef.header,
                                                    header.getContext()
                                                )}
                                        </TableHead>
                                    )
                                })}
                            </TableRow>
                        ))}
                    </TableHeader>
                    <TableBody>
                        {table.getRowModel().rows?.length ? (
                            table.getRowModel().rows.map((row) => (
                                <TableRow
                                    key={row.id}
                                    data-state={row.getIsSelected() && "selected"}
                                >
                                    {row.getVisibleCells().map((cell) => (
                                        <TableCell key={cell.id}>
                                            {flexRender(cell.column.columnDef.cell, cell.getContext())}
                                        </TableCell>
                                    ))}
                                </TableRow>
                            ))
                        ) : (
                            <TableRow>
                                <TableCell colSpan={columns.length} className="h-24 text-center">
                                    No results.
                                </TableCell>
                            </TableRow>
                        )}
                    </TableBody>
                </Table>
            </div>
            <div className="flex gap-4 mb-4">
                <DropdownMenu>
                    <DropdownMenuTrigger asChild>
                        <Button variant="outline">Считаем от: {day1}</Button>
                    </DropdownMenuTrigger>
                    <DropdownMenuContent>
                        {series.map(day => (
                            <DropdownMenuItem key={day} onClick={() => setDay1(day)}>
                                {day}
                            </DropdownMenuItem>
                        ))}
                    </DropdownMenuContent>
                </DropdownMenu>

                <DropdownMenu>
                    <DropdownMenuTrigger asChild>
                        <Button variant="outline">Считаем до: {day2}</Button>
                    </DropdownMenuTrigger>
                    <DropdownMenuContent className="">
                        {series.map(day => (
                            <DropdownMenuItem key={day} onClick={() => setDay2(day)}>
                                {day}
                            </DropdownMenuItem>
                        ))}
                    </DropdownMenuContent>
                </DropdownMenu>
            </div>
        </div>
    )
}
