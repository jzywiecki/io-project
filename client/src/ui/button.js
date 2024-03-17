export const Button=({className, children, ...props})=>(
<button {...props} className={"p-2 bg-sky-500 rounded w-full self-center text-white "+className}>
    {children}
</button>)