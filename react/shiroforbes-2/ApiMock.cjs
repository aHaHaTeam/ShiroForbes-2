const express = require('express');
const cors = require('cors')
const {useEffect} = require("react");
const app = express();

app.use(cors());
app.use(express.json());

app.use('/api/v2/auth/signin', (req, res) => {
    const {login, password} = req.body;

    if (login === "test" && password === "pass") {
        res.json({
            accessToken: "test123",
            refreshToken: "bebebe239",
            role: "tester"
        });
    } else {
        res.status(401).json({error: "Invalid credentials"});
    }
});

app.use('/api/v2/test/profile', (req, res) => {
    const authHeader = req.headers.authorization;

    if (!authHeader) {
        return res.status(401).json({error: "No Authorization header"});
    }

    const token = authHeader.split(" ")[0];

    if (token === "test123") {
        res.json({
            "name": "Вика Андриенко",
            mathStats: {
                rating: 2025,
                wealthRank: 15,
                tasks: 239,
                grobs: 0,
                algebra: 88,
                comba: 90,
                geoma: 89,
                tch: 91
            },
            wealthStats: {
                balance: 2025,
                wealthRank: 15,
                total: 239,
                transactions: 0,
                spent: 88,
                investments: 90,
                isInvesting: true,

            },
        });
    } else {
        res.status(401).json({error: "Invalid token"});
    }
});


app.use('/api/v2/price-list', (req, res) => {
    res.json([
        {
            name: "Песня на подъём",
            price: "300Zlt",
        },
        {
            name: "Песня на отбой",
            price: "400Zlt",
        },
        {
            name: "Слова паразиты на отбой",
            price: "40000Zlt",
        }]);
});

app.use("/api/v2/transactions/state", (req, res) => {
    const authHeader = req.headers.authorization;

    if (!authHeader) {
        return res.status(401).json({error: "No Authorization header"});
    }

    const token = authHeader.split(" ")[0];

    if (token === "test123") {
        res.json([
            {name: "Alice", balance: 100},
            {name: "Bob", balance: 150},
            {name: "Charlie", balance: 200},
        ]);
    } else {
        res.status(401).json({error: "Invalid token"});
    }
});

app.use("/api/v2/transactions/presets", (req, res) => {
    const authHeader = req.headers.authorization;

    if (!authHeader) {
        return res.status(401).json({error: "No Authorization header"});
    }

    const token = authHeader.split(" ")[0];

    if (token === "test123") {
        res.json([
            {label: "Зарядка", message: "Участие в зарядке", amount: "30"},
            {label: "Отбой", message: "Успешный отбой", amount: "25"},
        ]);
    } else {
        res.status(401).json({error: "Invalid token"});
    }
})

app.use("/api/v2/rating/countryside", (req, res) => {
    const authHeader = req.headers.authorization;

    if (!authHeader) {
        return res.status(401).json({error: "No Authorization header"});
    }

    const token = authHeader.split(" ")[0];

    if (token === "test123") {
        res.json([
            [
                {name: "Alice", absoluteRating: 1580},
                {name: "Bob", absoluteRating: 1540},
                {name: "Charlie", absoluteRating: 152},
            ],
            [
                {name: "Bob", absoluteRating: 1590},
                {name: "Alice", absoluteRating: 1565},
                {name: "Charlie", absoluteRating: 152},
            ],
            [
                {name: "Charlie", absoluteRating: 160},
                {name: "Bob", absoluteRating: 1560},
                {name: "Alice", absoluteRating: 1555},
            ]
        ]);
    } else {
        res.status(401).json({error: "Invalid token"});
    }
});

app.use("/api/v2/rating/urban", (req, res) => {
    const authHeader = req.headers.authorization;

    if (!authHeader) {
        return res.status(401).json({error: "No Authorization header"});
    }

    const token = authHeader.split(" ")[0];

    if (token === "test123") {
        res.json([
            [
                {name: "Anna", absoluteRating: 1580},
                {name: "Barry", absoluteRating: 1540},
                {name: "Clyde", absoluteRating: 152},
            ],
            [
                {name: "Barry", absoluteRating: 1590},
                {name: "Anna", absoluteRating: 1565},
                {name: "Clyde", absoluteRating: 152},
            ],
            [
                {name: "Clyde", absoluteRating: 160},
                {name: "Barry", absoluteRating: 1560},
                {name: "Anna", absoluteRating: 1555},
            ]
        ]);
    } else {
        res.status(401).json({error: "Invalid token"});
    }
});


app.use("/api/v2/transactions/urban", (req, res) => {
    const authHeader = req.headers.authorization;

    if (!authHeader) {
        return res.status(401).json({error: "No Authorization header"});
    }

    const token = authHeader.split(" ")[0];

    if (token === "test123") {
        res.json( [
            { id: "1", name: "Саша", message: "Покупка товара", amount: 1500, date: "2025-08-01" },
            { id: "2", name: "Маша", message: "Оплата подписки", amount: 799, date: "2025-08-02" },
            { id: "3", name: "Даша", message: "Перевод", amount: 3200, date: "2025-08-03" },
        ]);
    } else {
        res.status(401).json({error: "Invalid token"});
    }
});

app.use("/api/v2/transactions/countryside", (req, res) => {
    const authHeader = req.headers.authorization;

    if (!authHeader) {
        return res.status(401).json({error: "No Authorization header"});
    }

    const token = authHeader.split(" ")[0];

    if (token === "test123") {
        res.json( [
            { id: "1", name: "Саша", message: "Покупка товара", amount: 1500, date: "2025-08-01" },
            { id: "2", name: "Маша", message: "Оплата подписки", amount: 799, date: "2025-08-02" },
            { id: "3", name: "Даша", message: "Перевод", amount: 3200, date: "2025-08-03" },
        ]);
    } else {
        res.status(401).json({error: "Invalid token"});
    }
});

app.use("/api/v2/test/transactions/", (req, res) => {
    const authHeader = req.headers.authorization;

    if (!authHeader) {
        return res.status(401).json({error: "No Authorization header"});
    }

    const token = authHeader.split(" ")[0];

    if (token === "test123") {
        res.json( [
            { id: "1", name: "Саша", message: "Покупка товара", amount: 1500, date: "2025-08-01" },
            { id: "2", name: "Саша", message: "Оплата подписки", amount: 799, date: "2025-08-02" },
            { id: "3", name: "Саша", message: "Перевод", amount: 3200, date: "2025-08-03" },
        ]);
    } else {
        res.status(401).json({error: "Invalid token"});
    }
});

app.listen(8080, () => console.log('API is running on http://localhost:8080/api/v2/auth/signin'));