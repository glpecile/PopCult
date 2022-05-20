export default function Loader() {
    return (
        <div className="animate-pulse space-y-3">
            {/*<div className="bg-slate-300 h-16 w-full rounded-md"> </div>*/}
            <div className="flex justify-between">
                <div className="bg-slate-300 h-8 w-96 rounded-full my-4"> </div>
                <div className="bg-slate-300 h-8 w-36 rounded-full my-4"> </div>
            </div>
            <div className="grid lg:grid-cols-4 md:grid-cols-2 sm:grid-cols-1 gap-3 pb-3">
                <div className="h-80 w-48 bg-slate-300 rounded-lg"> </div>
                <div className="h-80 w-48 bg-slate-300 rounded-lg"> </div>
                <div className="h-80 w-48 bg-slate-300 rounded-lg"> </div>
                <div className="h-80 w-48 bg-slate-300 rounded-lg"> </div>
            </div>
        </div>
    );
}