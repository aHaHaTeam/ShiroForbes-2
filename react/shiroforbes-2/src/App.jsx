import './App.css'

import {Test} from "@/pages/Test.jsx";
import {BrowserRouter, Routes, Route} from "react-router-dom";
import {Profile} from "@/pages/Profile.jsx";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Test/>} />
                <Route path="/profile" element={<Profile/>} />
                <Route path="*" element={<Test />} />
            </Routes>
        </BrowserRouter>
    )
}

export default App
