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

export function LoginForm({
                              className,
                              ...props
                          }) {
    console.log(className)
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
                    <form>
                        <div className="flex flex-col gap-6">
                            <div className="grid gap-3">
                                <Label htmlFor="login">Login</Label>
                                <Input id="login" placeholder="koposovt" required/>
                            </div>
                            <div className="grid gap-3">
                                <div className="flex items-center">
                                    <Label htmlFor="password">Password</Label>
                                    <a
                                        href="#"
                                        className="ml-auto inline-block text-sm underline-offset-4 hover:underline">
                                        Забыл(а) пароль?
                                    </a>
                                </div>
                                <Input id="password" type="password" required/>
                            </div>
                            <div className="flex flex-col gap-3">
                                <Button type="submit" variant="outline" className="w-full">
                                    Login
                                </Button>
                            </div>
                        </div>
                        <div className="mt-4 text-center text-sm">
                            Нет аккаунта?{" "}
                            <a href="#" className="underline underline-offset-4">
                                Создать аккаунт
                            </a>
                        </div>
                    </form>
                </CardContent>
            </Card>
        </div>
    );
}
