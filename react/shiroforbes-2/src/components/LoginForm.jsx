import {cn} from "@/utils/tw-utils.js"
import {Button} from "@/components/ui/button"
import {
    Card,
    CardContent,
    CardDescription,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"
import {Input} from "@/components/ui/input"
import {Label} from "@/components/ui/label"
import Logo from "@/components/Logo/Logo.jsx";
import {useState} from "react";
import {useAuth} from "@/utils/AuthContext.jsx";

import {useNavigate} from "react-router-dom";
import {useData} from "@/utils/DataContext.jsx";
import {toast} from "sonner";


export function LoginForm({
                              className,
                              ...props
                          }) {
    const [login, setLogin] = useState("");
    const [password, setPassword] = useState("");

    const navigate = useNavigate();

    const auth = useAuth();

    const userData = useData();

    async function authorize(login, password) {
        auth.Logout();
        const res = await fetch("api/auth/signin", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({login, password})
        });
        if (res.ok) {
            const data = await res.json();
            auth.Login({
                accessToken: data.accessToken,
                refreshToken: data.refreshToken,
                role: data.role,
                login: login
            });
            if (data.role === "student") {
                userData.setCamp({camp: "Countryside"});
            }
            userData.rememberLogin({username: login});
            navigate("/");
        } else {
            console.error("Login failed. Please check your credentials and try again.");
            toast("Неверный логин/пароль");
        }
    }

    return (
        <div className={cn("flex flex-col gap-6", className)} {...props}>
            <Card>
                <CardHeader>
                    <CardTitle><Logo className="text-kinda-big"/></CardTitle>
                    <CardDescription>
                        Введите логин и пароль
                    </CardDescription>
                </CardHeader>
                <CardContent>
                    <form onSubmit={e => {
                        e.preventDefault();
                        authorize(login, password);
                    }}>
                        <div className="flex flex-col gap-6">
                            <div className="grid gap-3">
                                <Label htmlFor="login">Login</Label>
                                <Input id="login" placeholder="koposovt" required
                                       onChange={e => setLogin(e.target.value)}/>
                            </div>
                            <div className="grid gap-3">
                                <div className="flex items-center">
                                    <Label htmlFor="password">Password</Label>
                                    {/*<a*/}
                                    {/*    href="/"*/}
                                    {/*    className="ml-auto inline-block text-sm underline-offset-4 hover:underline">*/}
                                    {/*    Забыл(а) пароль?*/}
                                    {/*</a>*/}
                                </div>
                                <Input id="password" type="password" placeholder="qwerty123" required
                                       onChange={e => setPassword(e.target.value)}/>
                            </div>
                            <div className="flex flex-col gap-3">
                                <Button type="submit" variant="outline" className="w-full">
                                    Login
                                </Button>
                            </div>
                        </div>
                        {/*<div className="mt-4 text-center text-sm">*/}
                        {/*    Нет аккаунта?{" "}*/}
                        {/*    <a href="#" className="underline underline-offset-4">*/}
                        {/*        Создать аккаунт*/}
                        {/*    </a>*/}
                        {/*</div>*/}
                    </form>
                </CardContent>
            </Card>
        </div>
    );
}
