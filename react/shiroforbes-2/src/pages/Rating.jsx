import {SidebarArea} from "@/components/Sidebar/SidebarArea.jsx";
import Header from "@/components/Header.jsx";

import {Swiper, SwiperSlide} from "swiper/react";
import {RatingTable} from "@/components/RatingTable.jsx";



export function Rating({
                           className,
                           style,
                           children,
                           ...props
                       }) {
    return (
        <div className={className} {...props}>
            <SidebarArea>
                <Header/>
                <div className="flex-1 overflow-hidden sm:w-full xl:w-[97%]">
                    <Swiper className="w-full h-full" spaceBetween={30} slidesPerView="auto"
                            breakpoints={{
                                768: {
                                    slidesPerView: 2
                                }
                            }}>
                        <SwiperSlide>
                            <RatingTable/>
                        </SwiperSlide>
                        <SwiperSlide>
                            <RatingTable isUrban={true}/>
                        </SwiperSlide>
                        {children}
                    </Swiper>
                </div>
            </SidebarArea>
        </div>
    )
}