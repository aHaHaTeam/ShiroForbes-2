import {useState} from "react"
import {Achievement, AchievementSplash} from "@/components/Achievement.jsx";

export function AchievementsGrid({items}) {
    const [selected, setSelected] = useState(null)
    if(items.length === 0){
        return (
            <h2>Пока нет ачивок</h2>
        )
    }
    return (
        <>

            <div className="grid grid-cols-2 gap-4 md:gap-8">
                {items.map(item => (
                    <button
                        key={item.id}
                        onClick={() => setSelected(item)}
                        className="rounded-lg overflow-hidden focus:outline-none bg-gray-100"
                    >
                        <Achievement pictureUrl={item.pictureUrl} title={item.title} ></Achievement>
                    </button>
                ))}
            </div>

            {selected && (
                <AchievementSplash
                    onClose={() => setSelected(null)}
                    title={selected.title}
                    pictureUrl={selected.pictureUrl}
                    description={selected.description}
                    date={selected.date}
                />
            )}
        </>
    )
}
