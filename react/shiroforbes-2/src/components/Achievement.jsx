export function Achievement({
                            title,
                            pictureUrl,
                            className,
                            style,
                            ...props
                        }) {
    return (
        <div style={style} className={className} {...props}>
            <div className="flex justify-center p-2">
                <div className="w-32 aspect-square rounded-full overflow-hidden bg-gray-100">
                    <img
                        src={pictureUrl}
                        alt={title}
                        className="w-full h-full object-cover"
                    />
                </div>
            </div>
        </div>
    )
}

import { createPortal } from "react-dom"
import { useEffect } from "react"

export function AchievementSplash(props) {
    const {
        onClose,
        title,
        description,
        date,
        pictureUrl,
    } = props

    useEffect(() => {
        document.body.style.overflow = "hidden"
        return () => {
            document.body.style.overflow = ""
        }
    }, [])
    const formattedDate = new Date(date).toLocaleDateString("ru-RU")

    return createPortal(
        <div className="fixed inset-0 z-[1000] flex items-center justify-center">

            {/* overlay */}
            <div
                className="absolute inset-0 bg-black/40"
                onClick={onClose}
            />

            {/* card */}
            <div
                className="relative bg-white rounded-2xl shadow-xl
                   w-[90vw] max-w-lg max-h-[85vh] overflow-y-auto
                   p-6"
            >
                <button
                    onClick={onClose}
                    className="absolute top-4 right-4 text-gray-500 hover:text-black text-2xl"
                >
                    ✕
                </button>

                <div className="mb-6 flex justify-center">
                    <div className="w-48 aspect-square rounded-xl overflow-hidden bg-gray-100">
                        <img
                            src={pictureUrl}
                            alt={title}
                            className="w-full h-full object-cover"
                        />
                    </div>
                </div>

                <h2 className="text-xl font-semibold mb-2">
                    {title}
                </h2>

                <p className="text-gray-600">
                    {description}
                </p>

                <p className="text-sm text-gray-400 mt-2">
                    {formattedDate}
                </p>
            </div>
        </div>,
        document.body
    )
}

