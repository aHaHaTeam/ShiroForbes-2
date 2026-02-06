import {createContext, useContext, useState} from "react";

const DataContext = createContext(null);

const normalizeCampType = (camp) => {
    if (!camp) return camp;
    const map = {
        "Загородный": "Countryside",
        "Городской1": "Urban1",
        "Городской2": "Urban2",
    };
    return map[camp] || camp;
};

export function DataProvider({children}) {
    const [username, setUsername] = useState(() => localStorage.getItem("username"));
    const [cat, setCat] = useState(() => localStorage.getItem("cat"));
    const [campType, setCampType] = useState(() => {
        const stored = localStorage.getItem("campType");
        const normalized = normalizeCampType(stored);
        if (normalized !== stored && normalized) {
            localStorage.setItem("campType", normalized);
        }
        if (localStorage.getItem("campType") === null){

        }
        return normalized;
    });

    const rememberLogin = ({username}) => {
        setUsername(username);
        localStorage.setItem("username", username);
    }

    const setCamp = ({camp}) => {
        const normalized = normalizeCampType(camp);
        setCampType(normalized);
        if (normalized) {
            localStorage.setItem("campType", normalized);
        } else {
            localStorage.removeItem("campType");
        }
    }

    return (
        <DataContext.Provider value={{username, cat, campType, setCamp, rememberLogin}}>
            {children}
        </DataContext.Provider>)
}

export function useData() {
    return useContext(DataContext);
}