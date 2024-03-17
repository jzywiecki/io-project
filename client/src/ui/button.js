export const Button=({className, children, ...props})=>(
<button {...props} className={"p-2 bg-sky-500 rounded self-center text-white "+className}>
    {children}
</button>)