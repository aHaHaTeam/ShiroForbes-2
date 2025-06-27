import {useState} from 'react'
import shirovlasik from './assets/shiro&vlasik.png'
import sillycat from './assets/silly-cat-bleh.jpg'
import './App.css'

function App() {
    const [count, setCount] = useState(0)

    return (
        <>
            <div className="flex rounded-2xl items-center gap-4 p-4 bg-gray-100 justify-center">
                <div className="flex container w-1/2 justify-center">
                    <a href="https://youtube.com" target="_blank">
                        <img src={sillycat} className="logo" alt="Cat logo"/>
                    </a>
                    <a href="http://shiriforbes.ru" target="_blank">
                        <img src={shirovlasik} className="logo" alt="Shiriforbes logo"/>
                    </a>
                </div>
            </div>


            <div className="card">
                <button onClick={() => setCount((count) => count + 1)}>
                    count is {count}
                </button>
            </div>
        </>
    )
}

export default App
