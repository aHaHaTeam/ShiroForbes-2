import {createContext, useContext, useState} from "react";

const DataContext = createContext(null);


export function DataProvider({children}) {
    const [username, setUsername] = useState(() => localStorage.getItem("username"));
    const [cat, setCat] = useState(() => localStorage.getItem("cat"));
    const [campType, setCampType] = useState(localStorage.getItem("campType"));

    const rememberLogin = ({username}) => {
        setUsername(username);
        localStorage.setItem("username", username);
    }

    const setCamp = ({camp}) => {
        setCampType(camp);
        localStorage.setItem("campType", camp);
    }

    return (
        <DataContext.Provider value={{username, cat, campType, setCamp, rememberLogin}}>
            {children}
        </DataContext.Provider>)
}

export function useData() {
    return useContext(DataContext);
}


