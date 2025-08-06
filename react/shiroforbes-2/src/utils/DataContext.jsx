import {createContext, useContext, useState} from "react";

const DataContext = createContext(null);


export function DataProvider({children}) {
    const [username, setUsername] = useState(() => localStorage.getItem("username"));
    const [cat, setCat] = useState(() => localStorage.getItem("cat"));

    const rememberLogin = ({username}) => {
        setUsername(username);
        localStorage.setItem("username", username);

    }

    return (
        <DataContext.Provider value={{username, cat, rememberLogin}}>
            {children}
        </DataContext.Provider>)
}

export function useData() {
    return useContext(DataContext);
}


