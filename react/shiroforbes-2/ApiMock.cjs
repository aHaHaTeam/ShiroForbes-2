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

app.use('/api/v2/test/stats', (req, res) => {
    const authHeader = req.headers.authorization;

    if (!authHeader) {
        return res.status(401).json({error: "No Authorization header"});
    }

    // authHeader ожидается в виде: "Bearer test123"
    const token = authHeader.split(" ")[0];

    if (token === "test123") {
        res.json({
            "name": "Вика Андриенко",
            "rating": 2025,
            "rank": 15,
            "tasks": 239,
            "grobs": 0,
            "algebra": 88,
            "comba": 90,
            "geoma": 89,
            "tch": 91
        });
    } else {
        res.status(401).json({error: "Invalid token"});
    }
});


app.use('/api/v2/name/wealth', (req, res) => {
    const authHeader = req.headers.authorization; // 👈 достаём заголовок

    if (!authHeader) {
        return res.status(401).json({error: "No Authorization header"});
    }

    // authHeader ожидается в виде: "Bearer test123"
    const token = authHeader.split(" ")[0];

    if (token === "test123") {
        res.json({
            name: "Вика Андриенко",
            balance: 2025,
            rank: 15,
            total: 239,
            transactions: 0,
            spent: 88,
            investments: 90,
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
})
app.listen(8080, () => console.log('API is running on http://localhost:8080/api/v2/auth/signin'));