import './App.css'

import {Test} from "@/pages/Test.jsx";
import {BrowserRouter, Routes, Route} from "react-router-dom";
import {Profile} from "@/pages/Profile.jsx";
import {MoneyDistribution} from "@/pages/MoneyDistribution.jsx";
import {LoginForm} from "@/components/LoginForm.jsx";
import {Rating} from "@/pages/Rating.jsx";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/login" element={<LoginForm/>}/>
                <Route path="/money-distribution" element={<MoneyDistribution/>}/>
                <Route path="rating" element={<Rating/>}/>
                <Route path="/" element={<Rating/>}/>
                <Route path="/profile" element={<Profile/>}/>
                <Route path="/test" element={<Test/>}/>
            </Routes>
        </BrowserRouter>
    )
}

export default App
