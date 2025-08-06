import {useEffect, useState} from "react";
import {useApiFetch} from "@/utils/api.js";
import {useAuth} from "@/utils/AuthContext.jsx";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCaretDown, faCaretUp, faMinus} from "@fortawesome/free-solid-svg-icons";
import {Button} from "@/components/ui/button.jsx";
import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuTrigger
} from "@/components/ui/dropdown-menu.jsx";
import {Table, TableBody, TableCell, TableHead, TableHeader, TableRow,} from "@/components/ui/table.jsx"
import {flexRender, getCoreRowModel, useReactTable} from "@tanstack/react-table";

const columns = [
    {
        accessorKey: "place",
        header: "Place",
        cell: ({row}) => {
            const rating = row.original.place;
            const delta = row.original.deltaPlace;
            let deltaElement;

            if (delta > 0) {
                deltaElement = <span className="text-green-600 flex flex-col items-center leading-0">
                                    <span className="text-xs">+{delta}</span>
                                    <FontAwesomeIcon icon={faCaretUp} className="text-xs"/>
                               </span>;
            } else if (delta < 0) {
                deltaElement = <span className="text-red-600 flex flex-col items-center">
                                    <span className="text-xs">{delta}</span>
                                    <FontAwesomeIcon icon={faCaretDown} className="text-xs"/>
                               </span>;
            } else {
                deltaElement = <span className="text-gray-400 flex flex-col items-center">
                                    <FontAwesomeIcon icon={faMinus}/>
                               </span>;
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

async function compareRatings(data, day1, day2) {
    const oldData = data[day1];
    const newData = data[day2];

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


export function RatingTable({isUrban = false}) {
    const [data, setData] = useState([]);
    const [preparedData, setPreparedData] = useState([]);
    const [day1, setDay1] = useState(1);
    const [day2, setDay2] = useState(2);
    const apiFetch = useApiFetch();
    const role = useAuth().role;
    const isAdmin = ["tester", "admin"].includes(role);
    const url = `/api/rating/${isUrban ? "urban" : "countryside"}`;
    const [series, setSeries] = useState([]);
    useEffect(() => {
        apiFetch(url)
            .then((res) => {
                if (!res.ok) {
                    throw new Error(`HTTP ${res.status} ${url}`);
                }
                return res.json();
            }).then((res) => {
            setData(res);
            setSeries(Array.from({length: res.length}, (_, i) => i + 1));

        })
    }, [])

    useEffect(() => {
        compareRatings(data, day1, day2).then(setPreparedData);
    }, [data, day1, day2]);

    const table = useReactTable({
        data: preparedData,
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
                                    className="odd:bg-white even:bg-gray-200"
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
            <div className="flex gap-4 mb-4 justify-evenly w-full">
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
                {isAdmin ?
                    <Button onClick={async () => {
                        try {
                            const response = await apiFetch(url, {
                                method: "POST",
                            });
                            if (!response.ok) {
                                console.error(`Ошибка: ${response.status}`);
                                return;
                            }
                            const result = await response.json();
                            console.log("Успешно обновлено:", result);
                        } catch (error) {
                            console.error("Сетевая ошибка:", error);
                        }
                    }}>Опубликовать</Button> : null}
            </div>
        </div>
    )
}