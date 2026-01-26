import {useEffect, useState} from "react";
import {useApiFetch} from "@/utils/api.js";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCaretDown, faCaretUp, faMinus} from "@fortawesome/free-solid-svg-icons";
import {Button} from "@/components/ui/button.jsx";
import {
    DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuTrigger
} from "@/components/ui/dropdown-menu.jsx";
import {Table, TableBody, TableCell, TableHead, TableHeader, TableRow,} from "@/components/ui/table.jsx"
import {flexRender, getCoreRowModel, useReactTable} from "@tanstack/react-table";
import {RoleBox} from "@/components/RoleBox.jsx";
import {useData} from "@/utils/DataContext.jsx";
import {toast} from "sonner";
import {useAuth} from "@/utils/AuthContext.jsx";

const alphabet = {
    "а": "a",
    "б": "b",
    "в": "v",
    "г": "g",
    "д": "d",
    "е": "e",
    "ё": "yo",
    "ж": "zh",
    "з": "z",
    "и": "i",
    "й": "y",
    "к": "k",
    "л": "l",
    "м": "m",
    "н": "n",
    "о": "o",
    "п": "p",
    "р": "r",
    "с": "s",
    "т": "t",
    "у": "u",
    "ф": "f",
    "х": "kh",
    "ц": "ts",
    "ч": "ch",
    "ш": "sh",
    "щ": "sch",
    "ъ": "",
    "ы": "y",
    "ь": "",
    "э": "e",
    "ю": "yu",
    "я": "ya"
};

function transliterate(text) {
    let res = "";

    for (let i = 0; i < text.length; i++) {
        let char = text[i].toLowerCase();

        if (char === " ") {
            const next = text[i + 1]?.toLowerCase();
            if (next && alphabet[next]) {
                res += alphabet[next];
            }
            break;
        }

        if (alphabet[char] !== undefined) {
            res += alphabet[char];
        }
    }

    return res;
}


const columns = [{
    accessorKey: "place", header: "Место", cell: ({row}) => {
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

        return (<div className="flex justify-center items-center gap-2 text-center">
            {deltaElement}
            <span className="text-center">{rating}</span>
        </div>);
    },
}, {
    accessorKey: "name", header: "Имя", cell: ({row}) => {
        return (
            <>
                <RoleBox permission={["student"]}>
                    <span className="text-center">{row.original.name}</span>
                </RoleBox>
                <RoleBox>
                    <a href={"/profile/" + transliterate(row.original.name)}><span
                        className="text-center">{row.original.name}</span> </a>
                </RoleBox>
            </>
        )
    },
}, {
    accessorKey: "deltaRating", header: "Δ Рейтинг",
}, {
    accessorKey: "rating", header: "Рейтинг",
}, {
    accessorKey: "solved", header: "Задачи"
}
]

function transpose(A) {
    const rows = A.length;
    const cols = A[0].length;
    const result = [];

    for (let col = 0; col < cols; col++) {
        const newRow = [];
        for (let row = 0; row < rows; row++) {
            newRow.push(A[row][col]);
        }
        result.push(newRow);
    }

    return result;
}

function reverseTranspose(A) {
    const rows = A.length;
    const cols = A[0].length;
    const result = [];

    for (let row = 0; row < rows; row++) {
        const newRow = [];
        for (let col = 0; col < cols; col++) {
            newRow.push(A[row][col]);
        }
        result.push(newRow);
    }

    return result;
}


async function compareRatings(data, day1, day2) {
    const oldData = data[day1];
    const newData = data[day2];

    const sortedOld = [...oldData].sort((a, b) => b.rating - a.rating);
    const sortedNew = [...newData].sort((a, b) => b.rating - a.rating);

    const oldMap = Object.fromEntries(sortedOld.map((item, index) => [item.lastName + " " + item.firstName, {
        ...item, place: index + 1
    }]));
    const newMap = Object.fromEntries(sortedNew.map((item, index) => [item.lastName + " " + item.firstName, {
        ...item, place: index + 1
    }]));

    const allNames = new Set([...Object.keys(oldMap), ...Object.keys(newMap)]);
    return Array.from(allNames).map((name) => {
        const oldEntry = oldMap[name] || {rating: 0, place: sortedOld.length + 1};
        const newEntry = newMap[name] || {rating: 0, place: sortedNew.length + 1};

        return {
            name,
            deltaRating: Math.round(newEntry.rating - oldEntry.rating),
            deltaPlace: oldEntry.place - newEntry.place,
            oldPlace: oldEntry.place,
            newPlace: newEntry.place,
            rating: Math.round(newEntry.rating),
            solved: Math.round(newEntry.solved),
            place: newEntry.place,
        };
    }).sort((a, b) => a.newPlace - b.newPlace);
}


export function RatingTable() {
    const [data, setData] = useState([]);
    const [preparedData, setPreparedData] = useState([]);
    const [day1, setDay1] = useState(0);
    const [day2, setDay2] = useState(0);
    const apiFetch = useApiFetch();
    const userData = useData();
    const auth = useAuth();
    const [series, setSeries] = useState([]);
    useEffect(() => {
        let url = `/api/rating/${userData.campType}`;
        if (auth.role.toLowerCase() === "admin") {
            url += '/new';
        }
        apiFetch(url)
            .then((res) => {
                if (!res.ok) {
                    throw new Error(`HTTP ${res.status} ${url}`);
                }
                return res.json()
            }).then((res) => {
            if (auth.role.toLowerCase() === "admin") {
                return transpose(res);
            }
            return reverseTranspose(res);
        }).then((res) => {
            console.log(res);
            setData(res);
            setDay1(Math.max(res.length - 2, 0));
            setDay2(res.length - 1);
            setSeries(Array.from({length: res.length}, (_, i) => {
                return `Серия ${i + 1}`;
            }));
        })
    }, [userData.campType]);

    useEffect(() => {
        compareRatings(data, day1, day2).then(setPreparedData);
    }, [data, day1, day2]);

    const table = useReactTable({
        data: preparedData, columns, getCoreRowModel: getCoreRowModel(),
    })

    return (<div>
        <div className="flex gap-4 mb-2 justify-evenly w-full pt-4">
            <DropdownMenu>
                <DropdownMenuTrigger asChild>
                    <Button className="bg-accent" variant="outline">Считаем от: {series[day1]}</Button>
                </DropdownMenuTrigger>
                <DropdownMenuContent className="bg-default-background">
                    {series.map((day, index) => (<DropdownMenuItem key={day} onClick={() => setDay1(index)}>
                        {day}
                    </DropdownMenuItem>))}
                </DropdownMenuContent>
            </DropdownMenu>

            <DropdownMenu>
                <DropdownMenuTrigger asChild>
                    <Button className="bg-accent" variant="outline">Считаем до: {series[day2]}</Button>
                </DropdownMenuTrigger>
                <DropdownMenuContent className="bg-default-background">
                    {series.map((day, index) => (<DropdownMenuItem key={day} onClick={() => setDay2(index)}>
                        {day}
                    </DropdownMenuItem>))}
                </DropdownMenuContent>
            </DropdownMenu>
        </div>
        <div className="rounded-md border">
            <Table>
                <TableHeader>
                    {table.getHeaderGroups().map((headerGroup) => (<TableRow key={headerGroup.id}>
                        {headerGroup.headers.map((header) => {
                            return (<TableHead key={header.id} className="text-center">
                                {header.isPlaceholder ? null : flexRender(header.column.columnDef.header, header.getContext())}
                            </TableHead>)
                        })}
                    </TableRow>))}
                </TableHeader>
                <TableBody>
                    {table.getRowModel().rows?.length ? (table.getRowModel().rows.map((row) => (<TableRow
                        key={row.id}
                        data-state={row.getIsSelected() && "selected"}
                        className="odd:bg-white even:bg-gray-200"
                    >
                        {row.getVisibleCells().map((cell) => (<TableCell key={cell.id}>
                            {flexRender(cell.column.columnDef.cell, cell.getContext())}
                        </TableCell>))}
                    </TableRow>))) : (<TableRow>
                        <TableCell colSpan={columns.length} className="h-24 text-center">
                            No results.
                        </TableCell>
                    </TableRow>)}
                </TableBody>
            </Table>
        </div>
        <div className="flex gap-4 mt-4 justify-evenly w-full">
            <RoleBox permission = {["admin", "tester"]}>
                <Button className="bg-accent" onClick={async () => {
                    try {
                        const url = `/api/rating/${userData.campType}`;
                        const response = await apiFetch(url, {
                            method: "POST",
                        });
                        if (!response.ok) {
                            console.error(`Ошибка: ${response.status}`);
                            toast(`Ошибка сервера: ${response.status}`)
                            return;
                        }
                        const result = await response
                        console.log("Успешно обновлено:", result);
                        toast("Успешно обновлено")
                    } catch (error) {
                        console.error("Ошибка:", error);
                        toast("Ошибка подключения");
                    }
                }}>Опубликовать</Button>
            </RoleBox>
        </div>
    </div>)
}