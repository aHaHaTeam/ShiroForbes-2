import {flexRender, getCoreRowModel, useReactTable} from "@tanstack/react-table"

import {
    Table, TableBody, TableCell, TableHead, TableHeader, TableRow,
} from "@/components/ui/table"
import {useEffect, useState} from "react";
import {Input} from "@/components/ui/input"
import {Button} from "@/components/ui/button"
import {cn} from "@/utils/tw-utils.js";
import {Checkbox} from "@/components/ui/checkbox.jsx";
import {SidebarArea} from "@/components/Sidebar/SidebarArea.jsx";
import Header from "@/components/Header.jsx";
import {useApiFetch} from "@/utils/api.js";


const columns = [
    {
        accessorKey: "select",
        header: ({table}) => (
            <Checkbox
                checked={
                    table.getIsAllPageRowsSelected() ||
                    (table.getIsSomePageRowsSelected() && "indeterminate")
                }
                onCheckedChange={(value) => table.toggleAllPageRowsSelected(!!value)}
                aria-label="Select all"
            />
        ),
        cell: ({row}) => (
            <Checkbox
                checked={row.getIsSelected()}
                onCheckedChange={(value) => row.toggleSelected(!!value)}
                aria-label="Select row"
            />
        )
    },
    {
        accessorKey: "name",
        header: "Name",
        cell: ({row}) => <div className="text-center w-full">{row.original.name}</div>,
    },
    {
        accessorKey: "balance",
        header: "Balance",
        cell: ({row}) => <div className="text-center w-full">{row.original.balance}</div>,
    },
];

function MoneyDistributionTable() {
    const [data, setData] = useState([]);
    const [presets, setPresets] = useState([]);
    const [message, setMessage] = useState("");
    const [amount, setAmount] = useState("");
    const [filter, setFilter] = useState("");
    const [filteredData, setFilteredData] = useState(data);

    const [showOptions, setShowOptions] = useState(false);

    const apiFetch = useApiFetch();
    useEffect(() => {
        apiFetch("/api/transactions/state").then((res) => {
            if (!res.ok) {
                throw new Error(`HTTP ${res.status}`);
            }
            return res.json();
        })
            .then((data) => setData(data))
            .catch((err) => console.error("Ошибка загрузки статуса по деньгам:", err));
    }, []);

    useEffect(() => {
        apiFetch("/api/transactions/presets").then((res) => {
            if (!res.ok) {
                throw new Error(`HTTP ${res.status}`);
            }
            return res.json();
        })
            .then((data) => setPresets(data))
            .catch((err) => console.error("Ошибка загрузки пресетов транзакций:", err));
    }, []);


    useEffect(() => {
        setFilteredData(data.filter(row =>
            row.name.toLowerCase().includes(filter.toLowerCase())
        ))
    }, [data, filter]);


    const table = useReactTable({
        data: filteredData,
        columns,
        getCoreRowModel: getCoreRowModel(),
        enableRowSelection: true,
    });

    const handleSubmit = () => {
        const selectedRows = table.getSelectedRowModel().rows.map(row => row.original.name);
        console.log(selectedRows);
        apiFetch("api/transactions/new", {
            method: "POST",
            body: JSON.stringify({
                names: selectedRows,
                message: message,
                amount: amount
            })
        }).then(console.log);
    };

    if (data == null) {
        return (
            <div>Загрузка...</div>
        )
    }
    return (
        <div className="space-y-4">
            <div className="relative">
                <Input
                    placeholder="Transaction message"
                    value={message}
                    onChange={(e) => setMessage(e.target.value)}
                    onFocus={() => setShowOptions(true)}
                    onBlur={() => setTimeout(() => setShowOptions(false), 150)} // задержка, чтобы можно кликнуть
                    className="w-full"
                />
                {showOptions && (
                    <div className="absolute z-10 w-full bg-white border rounded shadow">
                        {presets.map((preset) => (
                            <div
                                key={preset.label}
                                className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-left"
                                onMouseDown={() => {
                                    setMessage(preset.message);
                                    setAmount(preset.amount);
                                    setShowOptions(false);
                                }}
                            >
                                {preset.label}
                            </div>
                        ))}
                    </div>
                )}
            </div>

            <Input
                type="number"
                placeholder="Amount"
                value={amount}
                onChange={(e) => setAmount(e.target.value)}
                className="w-full"
            />

            <Input
                className="w-full"
                placeholder="Search by name"
                value={filter}
                onChange={(e) => setFilter(e.target.value)}
            />

            <div className="rounded-md border">
                <Table>
                    <TableHeader>
                        {table.getHeaderGroups().map((headerGroup) => (
                            <TableRow key={headerGroup.id}>
                                {headerGroup.headers.map((header) => {
                                    return (
                                        <TableHead key={header.id}
                                                   className={cn(header.column.columnDef.className, "text-center")}>
                                            {header.isPlaceholder
                                                ? null
                                                : flexRender(header.column.columnDef.header, header.getContext())}
                                        </TableHead>)
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
                                    className="odd:bg-white even:bg-gray-100 data-[state=selected]:bg-blue-100">
                                    {row.getVisibleCells().map((cell) => (
                                        <TableCell key={cell.id} className="text-center">
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

            <div className="flex justify-end">
                <Button onClick={handleSubmit}>Send</Button>
            </div>
        </div>
    );
}

export function MoneyDistribution({
                                      className,
                                      style,
                                      children,
                                      ...props
                                  }) {
    return (
        <div className={className} {...props}>
            <SidebarArea>
                <Header/>
                <MoneyDistributionTable/>
                {children}
            </SidebarArea>
        </div>
    )
}
