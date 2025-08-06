import * as React from "react"
import * as CheckboxPrimitive from "@radix-ui/react-checkbox"
import {CheckIcon} from "lucide-react"

import {cn} from "@/utils/tw-utils.js"

function Checkbox({
                      className,
                      ...props
                  }) {
    return (
        <CheckboxPrimitive.Root
            data-slot="checkbox"
            className={cn(
                "peer border-input bg-default-background dark:bg-inverse-background data-[state=checked]:bg-inverse-background data-[state=checked]:text-inverse-text dark:data-[state=checked]:bg-default-background dark:data-[state=checked]:text-default-text data-[state=checked]:border-primary focus-visible:border-ring focus-visible:ring-ring/50 aria-invalid:ring-destructive/20 dark:aria-invalid:ring-destructive/40 aria-invalid:border-destructive size-4 shrink-0 rounded-[4px] border shadow-xs transition-shadow outline-none focus-visible:ring-[3px] disabled:cursor-not-allowed disabled:opacity-50 flex items-center justify-center",
                className
            )}
            {...props}>
            <CheckboxPrimitive.Indicator
                data-slot="checkbox-indicator"
                className="text-current transition-none">
                <CheckIcon className="size-3.5"/>
            </CheckboxPrimitive.Indicator>
        </CheckboxPrimitive.Root>
    );
}

export {Checkbox}
