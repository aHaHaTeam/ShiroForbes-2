import {useEffect, useState} from "react";
import {flexRender, getCoreRowModel, useReactTable} from "@tanstack/react-table";
import {Table, TableBody, TableCell, TableHead, TableHeader, TableRow} from "@/components/ui/table.jsx";
import {useApiFetch} from "@/utils/api.js";

const columns = [
    {
        accessorKey: "name",
        header: "Услуга",
        cell: ({row}) => <div className="text-center w-full">{row.original.name}</div>,
    },
    {
        accessorKey: "price",
        header: "Цена",
        cell: ({row}) => <div className="text-center w-full">{row.original.price}</div>,
    },

]

export function ShopTable() {
    const [priceList, setPriceList] = useState(null)
    const apiFetch = useApiFetch();
    useEffect(() => {
        apiFetch("/api/price-list")
            .then((res) => {
                if (!res.ok) {
                    throw new Error(`HTTP ${res.status}`);
                }
                return res.json();
            })
            .then((data) => {
                setPriceList(data);
            })
            .catch((err) => console.error("Ошибка загрузки статистики:", err));
    }, []);

    const table = useReactTable({
        data: priceList,
        columns,
        getCoreRowModel: getCoreRowModel(),
    })

    if (priceList == null) {
        return (
            <div>
                Загрузка...
            </div>
        )
    }

    return (
        <div>
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
    )
}
