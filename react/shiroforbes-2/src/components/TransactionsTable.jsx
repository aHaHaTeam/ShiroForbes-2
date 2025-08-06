import React, {useEffect, useState} from "react";
import {Table, TableBody, TableCell, TableHead, TableHeader, TableRow} from "@/components/ui/table";
import {useData} from "@/utils/DataContext.jsx";
import {useApiFetch} from "@/utils/api.js";


export function TransactionGroupTable({isUrban = false}) {
    const url = `/api/transactions/${isUrban ? "urban" : "countryside"}`;
    const apiFetch = useApiFetch();
    const [transactions, setTransactions] = useState([])

    useEffect(() => {
        apiFetch(url).then((res) => {
            if (!res.ok) {
                throw new Error(`HTTP ${res.status}`);
            }
            return res.json();
        }).then((data) => {
            setTransactions(data);
        }).catch((err) => console.error("Ошибка загрузки списка транзакций:", err));
    }, []);

    return (
        <div className="rounded-xl border shadow-md p-4">
            <h2 className="text-xl font-semibold mb-4">Таблица транзакций</h2>
            <Table>
                <TableHeader>
                    <TableRow>
                        <TableHead className="text-left">Имя</TableHead>
                        <TableHead className="text-left">Сообщение</TableHead>
                        <TableHead className="text-left">Размер</TableHead>
                        <TableHead className="text-left">Дата</TableHead>
                    </TableRow>
                </TableHeader>
                <TableBody>
                    {transactions.map((tx) => (
                        <TableRow key={tx.id}>
                            <TableCell className="text-left">{tx.name}</TableCell>
                            <TableCell className="text-left">{tx.message}</TableCell>
                            <TableCell className="text-left">{tx.amount} ₽</TableCell>
                            <TableCell className="text-left">{tx.date}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </div>
    );
}

export function TransactionProfileTable() {
    const userData = useData();
    const apiFetch = useApiFetch();
    const [transactions, setTransactions] = useState([])

    useEffect(() => {
        apiFetch(`/api/${userData.username}/transactions`).then((res) => {
            if (!res.ok) {
                throw new Error(`HTTP ${res.status}`);
            }
            return res.json();
        }).then((data) => {
            setTransactions(data);
        }).catch((err) => console.error("Ошибка загрузки списка транзакций:", err));
    }, []);

    return (
        <div className="rounded-xl border shadow-md p-4 mt-4">
            <h2 className="text-xl font-semibold mb-4">Таблица транзакций</h2>
            <Table>
                <TableHeader>
                    <TableRow>
                        <TableHead className="text-left">Сообщение</TableHead>
                        <TableHead className="text-left">Размер</TableHead>
                        <TableHead className="text-left">Дата</TableHead>
                    </TableRow>
                </TableHeader>
                <TableBody>
                    {transactions.map((tx) => (
                        <TableRow key={tx.id}>
                            <TableCell className="text-left">{tx.message}</TableCell>
                            <TableCell className="text-left">{tx.amount} ₽</TableCell>
                            <TableCell className="text-left">{tx.date}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </div>
    );
}