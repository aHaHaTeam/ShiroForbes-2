import './App.css'

import {Test} from "@/pages/Test.jsx";
import {BrowserRouter, Routes, Route} from "react-router-dom";
import {Profile} from "@/pages/Profile.jsx";
import {MoneyDistribution} from "@/pages/MoneyDistribution.jsx";
import {Rating} from "@/pages/Rating.jsx";
import {Shop} from "@/pages/Shop.jsx";
import {AuthProvider} from "@/utils/AuthContext.jsx";
import {DataProvider} from "@/utils/DataContext.jsx";
import {TransactionsList} from "@/pages/TransactionsList.jsx";
import {LoginPage} from "@/pages/LoginPage.jsx";

function App() {
    return (
        <AuthProvider>
            <DataProvider>
                <BrowserRouter>
                    <Routes>
                        <Route path="/login" element={<LoginPage/>}/>
                        <Route path="/money-distribution" element={<MoneyDistribution/>}/>
                        <Route path="rating" element={<Rating/>}/>
                        <Route path="/" element={<Rating/>}/>
                        <Route path="/profile" element={<Profile/>}/>
                        <Route path="/test" element={<Test/>}/>
                        <Route path="/shop" element={<Shop/>}/>
                        <Route path="/transactions" element={<TransactionsList/>}/>
                    </Routes>
                </BrowserRouter>
            </DataProvider>
        </AuthProvider>
    )
}

export default App
