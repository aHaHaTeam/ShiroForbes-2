import "./Logo.css"
import {cn} from "@/utils/tw-utils.js";

function Logo({className}) {
    console.log(className)
    return (
        <div>
            <a className="Logo" href="/">
                <h1 className={cn("center", className)}>
                    <span className="text-shiro-blue">Shiro</span>
                    <span className="text-vlasik-orange">Forbes</span>
                </h1>
            </a>
        </div>
    )
}

export default Logo;