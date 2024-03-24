export const Card = ({ className, ...props }) => (
    <div
        className={'rounded-xl border border-neutral-200 text-neutral-950 shadow '+className}
        {...props}
    />
)

export const CardTitle = (({ className, ...props }) => (
    <h3
        className={'font-semibold leading-none tracking-tight '+className}
        {...props}
    />
));

export const CardDescription = (({ className, ...props }) => (
    <p
        className={'text-sm text-neutral-500 dark:text-neutral-400 '+className}
        {...props}
    />
));
